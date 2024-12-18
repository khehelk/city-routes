import React, {useState} from 'react'
import {CityDto, RouteDto, RouteUpdateDto, StopDto} from "../api/dto.ts";
import Input from "../ui/Input.tsx";
import {
    addInFavouriteReq,
    fetchCitiesByRegionCode,
    fetchRoutesByCityId,
    removeFromFavouriteReq
} from "../api/DirectoryApi.ts";
import {deleteRouteReq, fetchStopsByCityId, updateRouteReq} from "../api/AdminApi.ts";
import {getUserRoles, isAdmin} from "../utils/DecodedToken.ts";
import {fetchComments, sendCommentReq} from "../api/ReviewApi.ts";

const Directory = () => {
    const [cities, setCities] = useState<CityDto[]>([])
    const [routes, setRoutes] = useState<RouteDto[]>([])
    const [selectedCityId, setSelectedCityId] = useState<bigint | null>(null)
    const [regionCode, setRegionCode] = useState<number>(0)
    const [notification, setNotification] = useState<string | null>(null);
    const [errorNotification, setErrorNotification] = useState<string | null>(null);
    const [currentPage, setCurrentPage] = useState<number>(0);
    const [totalPages, setTotalPages] = useState<number>(0);
    const [updateRouteId, setUpdateRouteId] = useState<bigint | null>(null);
    const [updateRouteFrequencyRangeStart, setUpdateRouteFrequencyRangeStart] = useState<number | null>(null);
    const [updateRouteFrequencyRangeEnd, setUpdateRouteFrequencyRangeEnd] = useState<number | null>(null);
    const [updateRouteSelectedStops, setUpdateRouteSelectedStops] = useState<StopDto[]>([])
    const [updateRouteAllStops, setUpdateRouteAllStops] = useState<StopDto[]>([])
    const [isUpdating, setIsUpdating] = useState<boolean>(false)
    const [comments, setComments] = useState<{author: string, comment: string, date: Date}[][]>([])
    const [newComment, setNewComment] = useState<{ comment: string }[]>(new Array(20).fill({ comment: '' }))
    const [commentOffsets, setCommentOffsets] = useState<bigint[]>(Array.from({ length: 20 }, () => BigInt(0)))

    const showNotification = (message: string) => {
        setNotification(message);
        setTimeout(() => {
            setNotification(null);
        }, 3000);
    };

    const showErrorNotification = (message: string) => {
        setErrorNotification(message);
        setTimeout(() => {
            setErrorNotification(null);
        }, 3000);
    };

    const getCitiesByRegionCode = async () => {
        if (regionCode === null || regionCode === undefined) {
            console.warn("Код региона не указан");
            return;
        }

        fetchCitiesByRegionCode(regionCode)
            .then(res => {setCities(res)})
            .catch(err => showErrorNotification(err.response.data.message));
    }

    const getRoutesInCity = async (page: number | null) => {
        if (page === null || page === undefined) {
            page = currentPage;
        }
        if (regionCode === null || regionCode === undefined) {
            console.warn("Код региона не указан");
            return;
        }

        if (selectedCityId === null || selectedCityId === undefined) {
            console.warn("Выберите город");
            return;
        }

        fetchRoutesByCityId(selectedCityId, page)
            .then(res => {
                setRoutes(res.content)
                setCurrentPage(res.number)
                setTotalPages(res.totalPages - 1)
            })
            .catch(err => showErrorNotification(err.response.data.message));
    }

    const plusPage = async () => {
        setCurrentPage((prevPage) => {
            const newPage = prevPage + 1;
            getRoutesInCity(newPage);
            return newPage;
        });
    };

    const minusPage = async () => {
        setCurrentPage((prevPage) => {
            const newPage = prevPage - 1;
            getRoutesInCity(newPage);
            return newPage;
        });
    };

    const openUpdateModal = (route: RouteDto) => {
        setUpdateRouteId(route.id);
        setIsUpdating(true);
        setUpdateRouteFrequencyRangeStart(route.frequencyRangeStart)
        setUpdateRouteFrequencyRangeEnd(route.frequencyRangeEnd)
        setUpdateRouteSelectedStops(Array.from(route.stops.values()))
        getStopsByCityId()
    };

    const getStopsByCityId = () => {
        if (selectedCityId === null || selectedCityId < 0) {
            showNotification("Выберите город");
            return;
        }

        fetchStopsByCityId(selectedCityId)
            .then(res => {setUpdateRouteAllStops(res)})
            .catch(err => showErrorNotification(err.response.data.message));
    }

    const addStopToUpdate = (id: string) => {
        const stop = updateRouteAllStops.find(stop => stop.id.toString() === id)
        if (stop === undefined || stop === null) {
            return;
        }
        if (!updateRouteSelectedStops.some(stop => stop.id.toString() === id.toString() && stop.name === stop.name)) {
            setUpdateRouteSelectedStops((prevStops) => [...prevStops, stop]);
        }
    }

    const removeStopFromUpdate = (dto: StopDto) => {
        setUpdateRouteSelectedStops(updateRouteSelectedStops.filter((s) => s !== dto));
    }

    const updateRoute = () => {
        if (!updateRouteId) {
            showNotification("Выберите объект для изменения");
            return;
        }

        if (!updateRouteFrequencyRangeStart || !updateRouteFrequencyRangeEnd
            || updateRouteFrequencyRangeStart > updateRouteFrequencyRangeEnd) {
            showNotification("Введите корректный временной промежуток");
            return;
        }

        const dto: RouteUpdateDto = {
            frequencyRangeStart: updateRouteFrequencyRangeStart,
            frequencyRangeEnd: updateRouteFrequencyRangeEnd,
            stops: new Map(updateRouteSelectedStops.map((stop, index) => [index + 1, stop.id]))
        };

        updateRouteReq(updateRouteId, dto)
            .then(res => {
                showNotification(res);
                getRoutesInCity(currentPage);
            })
            .catch(err => showErrorNotification(err.response.data.message));
    }

    const deleteRoute = (dto: RouteDto) => {
        deleteRouteReq(dto.id)
            .then(res => {
                showNotification(res);
                console.log(res);
                getRoutesInCity(currentPage);
            })
            .catch(err => showErrorNotification(err.response.data.message));
    }

    const addInFavourite = (id: bigint) => {
        addInFavouriteReq(id)
            .then(res => {
                showNotification(res);
                console.log(res);
                getRoutesInCity(currentPage);
            })
            .catch(err => showErrorNotification(err.response.data.message));
    }

    const removeFromFavourite = (id: bigint) => {
        removeFromFavouriteReq(id)
            .then(res => {
                showNotification(res);
                console.log(res);
                getRoutesInCity(currentPage);
            })
            .catch(err => showErrorNotification(err.response.data.message));
    }

    function sendComment(index: number) {
        if (!newComment[index]) {
            showNotification("Поле комментария пустое");
            return;
        }

        sendCommentReq(newComment[index].comment, routes[index].id)
            .then(res => {
                showNotification(res);
                console.log(res);
                getComments(index);
            })
            .catch(err => showErrorNotification(err.response.data.message));
    }

    function getComments(index: number) {
        setCommentOffsets((commentOffsets) => {
            const prev = [...commentOffsets]
            prev[index] = BigInt(5)
            fetchComments(routes[index].id, commentOffsets[index])
                .then(res => {
                    setComments((comments) => {
                        const prev = [...comments]
                        prev[index] = res
                        return prev;
                    });
                })
                .catch(err => showErrorNotification(err.response.data.message));
            return prev;
        })
    }

    function getCommentsWithOffset(index: number) {
        setCommentOffsets((commentOffsets) => {
            const prev1 = [...commentOffsets]
            prev1[index] = prev1[index] + BigInt(5)
            fetchComments(routes[index].id, prev1[index])
                .then(res => {
                    setComments((comments) => {
                        const prev = [...comments]
                        prev[index] = res
                        setCommentOffsets((commentOffsets) => {
                            const prev = [...commentOffsets]
                            prev[index] = prev1[index] - BigInt(5) + BigInt(res.length)
                            return prev;
                        })
                        return prev;
                    });
                })
                .catch(err => {
                    showErrorNotification(err.response.data.message)
                    setCommentOffsets((commentOffsets) => {
                        const prev = [...commentOffsets]
                        prev[index] = prev[index] - BigInt(5)
                        return prev;
                    })
                });
            return prev1;
        })
    }

    function handleNewComment(value: string, index: number) {
        setNewComment(prevState => {
            const updatedComments = [...prevState]; // Copy the current state
            updatedComments[index] = { comment: value }; // Update the specific comment
            return updatedComments; // Return the new state
        });
    }

    return (
        <div className="min-w-full flex flex-col h-full">
            {notification && (
                <div style={styles.notification}>
                    {notification}
                </div>
            )}
            {errorNotification && (
                <div style={styles.errorNotification}>
                    {errorNotification}
                </div>
            )}
            <div className="flex w-21 mx-2 bg-white border rounded-lg shadow-md overflow-hidden">
                <Input type={"number"} className={"flex-1"} value={regionCode || ''} onChange={(value) => setRegionCode(value as number)}
                       placeholder={"Введите код региона"}/>
                <select
                    className="border bg-white border-gray-200 p-2 rounded flex-1"
                    onFocus={getCitiesByRegionCode}
                    onChange={(e) => setSelectedCityId(e.target.value as unknown as bigint)}
                    value={selectedCityId?.toString()}>
                    <option value="">Выберите город</option>
                    {cities.map((city) => (
                        <option key={city.id} value={city.id.toString()}>
                            {city.name}
                        </option>
                    ))}
                </select>
                {<button onClick={() => getRoutesInCity(currentPage)}
                    className="px-4 py-3 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all rounded-r-lg">
                    <img className="w-5 h-5 text-white" src="/img/search.svg" alt=""/>
                </button>}
            </div>
            <div className="routes-list flex-1">
                <div className="flex flex-col overflow-y-auto routes-list mx-2 border-b rounded-b-2xl">
                {routes.map((route, index) => (
                    <div className="my-2 p-2 border rounded-lg" key={Math.random()}>
                        <div className="flex flex-row items-center justify-between">
                            <div className="">
                                <div className="">
                                    <h1 className="text-2xl">Маршрут №{route.number}</h1>
                                </div>
                                <div className="">
                                    <p>Примерный интервал в минутах: {route.frequencyRangeStart} - {route.frequencyRangeEnd}</p>
                                </div>
                            </div>
                            <div className="">
                                <div className="">
                                    <button className="p-1">
                                        {route.isFavourite ?
                                            (
                                                <img onClick={() => removeFromFavourite(route.id)} className={`w-8 h-8 red-svg`} src="/img/favorite.svg"/>
                                            ) : (
                                                <img onClick={() => addInFavourite(route.id)} className={`w-8 h-8`} src="/img/favorite_border.svg"/>
                                            )
                                        }
                                    </button>
                                    {isAdmin() &&
                                        <>
                                            <button
                                                onClick={() => openUpdateModal(route)}
                                                className="bg-green-900 text-white rounded-2xl p-3 m-2">
                                                Обновить
                                            </button>
                                            <button
                                                onClick={() => deleteRoute(route)}
                                                className="bg-red-900 text-white rounded-2xl p-3 m-2">
                                                Удалить
                                            </button>
                                        </>
                                    }
                                </div>
                            </div>
                        </div>
                        <div className="flex flex-row overflow-x-auto">
                            {Array.from(route.stops.entries())
                                .sort(([keyA], [keyB]) => keyA - keyB)
                                .map(([key, value]) => (
                                    <div className="flex" key={Math.random()}>
                                        <div className="">{value.name}</div>
                                        {key < route.stops.size && <div className="mx-2">-</div>}
                                    </div>
                                ))
                            }
                        </div>
                        <div className="flex flex-col overflow-x-auto">
                            <div className="flex flex-col overflow-y-auto max-h-32">
                                {comments[index]?.map((comment, index) => (
                                    <div key={index} className="flex border p-2 rounded-lg flex-col ">
                                        <div className="flex flex-row">
                                            <h3 className="mr-2">{comment.date.toString().replace(/\.\d+Z$/, '').replace('T', ' ')}</h3>
                                            <h3 className="mr-2">{comment.author}</h3>
                                            <p>{comment.comment}</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                            <button onClick={() => getCommentsWithOffset(index)}
                                    className="px-4 py-3 my-2 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all rounded-lg">
                                Больше комментариев
                            </button>
                            {getUserRoles().length != 0 && <div className="flex flex-row">
                                <Input  type={"text"} className={"flex-1 rounded-l-lg"} value={newComment[index]?.comment || ''}
                                        onChange={(value) => handleNewComment(value as string, index)}
                                        placeholder={"Комментарий..."}/>
                                <button onClick={() => sendComment(index)}
                                        className="px-4 py-3 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all rounded-r-lg">
                                    Отправить
                                </button>
                            </div>}
                        </div>
                    </div>
                ))}
                </div>
            </div>
            <div className="flex my-2 align-center justify-center w-full">
                {currentPage > 0 && (
                    <button
                        className="px-4 py-3 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all rounded-lg"
                        onClick={minusPage}>
                        Назад
                    </button>
                )}
                {currentPage < totalPages && (
                    <>
                        <div className="mx-2 "/>
                        <button
                            className="px-4 py-3 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all rounded-lg"
                            onClick={plusPage}>
                            Вперёд
                        </button>
                    </>
                )}
            </div>
            {isUpdating && (
                <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center flex-col items-center">
                    <div className="bg-white rounded-lg p-5">
                        <h3 className="text-lg font-bold">Обновить маршрут</h3>
                        <div className="flex max-w-lg flex-col mt-4 w-200">
                            <Input type={"number"} value={updateRouteFrequencyRangeStart!}
                                   placeholder={"Введите начальное значение диапазона времени ожидания (минуты)"}
                                   onChange={(value) => setUpdateRouteFrequencyRangeStart(value as number)}/>
                            <Input type={"number"} value={updateRouteFrequencyRangeEnd!}
                                   placeholder={"Введите конечное значение диапазона времени ожидания (минуты)"}
                                   onChange={(value) => setUpdateRouteFrequencyRangeEnd(value as number)}/>
                            <select
                                className="border bg-white border-gray-200 p-2 rounded"
                                onFocus={getStopsByCityId}
                                onChange={(e) => addStopToUpdate(e.target.value)}
                                value="">
                                <option value="">Выберите остановку</option>
                                {updateRouteAllStops.map((stop) => (
                                    <option key={stop.id} value={stop.id.toString()}>
                                        {stop.name}
                                    </option>
                                ))}
                            </select>
                            <div className="flex overflow-x-auto space-x-4 border p-2 rounded">
                                {updateRouteSelectedStops.map((stop) => (
                                    <div
                                        key={stop.id}
                                        className="flex items-center space-x-2 border px-4 py-2 rounded bg-gray-100">
                                        <span>{stop.name}</span>
                                        <button
                                            className="text-red-500 font-bold"
                                            onClick={() => removeStopFromUpdate(stop)} >
                                            ×
                                        </button>
                                    </div>
                                ))}
                            </div>
                        </div>
                        <div className="flex justify-end mt-4">
                            <button
                                onClick={() => {
                                    updateRoute();
                                    getStopsByCityId();
                                }}
                                className="bg-green-900 text-white rounded-lg px-4 py-2 mr-2"
                            >
                                Обновить
                            </button>
                            <button
                                onClick={() => {
                                    setUpdateRouteFrequencyRangeStart(null)
                                    setUpdateRouteFrequencyRangeEnd(null)
                                    setUpdateRouteSelectedStops([])
                                    setIsUpdating(false)
                                }}
                                className="bg-red-900 text-white rounded-lg px-4 py-2"
                            >
                                Закрыть
                            </button>
                        </div>
                    </div>
                </div>
            )}


        </div>

    )
}

const styles = {
    notification: {
        position: "fixed",
        bottom: "20px",
        right: "20px",
        backgroundColor: "green",
        color: "white",
        padding: "10px 20px",
        borderRadius: "5px",
        boxShadow: "0 2px 5px rgba(0, 0, 0, 0.3)",
        zIndex: 1000,
    },
    errorNotification: {
        position: "fixed",
        bottom: "20px",
        right: "20px",
        backgroundColor: "red",
        color: "white",
        padding: "10px 20px",
        borderRadius: "5px",
        boxShadow: "0 2px 5px rgba(0, 0, 0, 0.3)",
        zIndex: 1000,
    },
};

export default Directory
