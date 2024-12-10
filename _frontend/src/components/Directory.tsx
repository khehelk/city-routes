import React, {useEffect, useState} from 'react'
import {CityDto, RouteDto, RoutesPageDto, RouteUpdateDto, StopDto} from "../api/dto.ts";
import Input from "../ui/Input.tsx";
import {fetchCitiesByRegionCode, fetchRoutesByCityId} from "../api/DirectoryApi.ts";
import {Component} from "../enums/Component.tsx";
import {deleteRouteReq, fetchStopsByCityId, updateRouteReq} from "../api/AdminApi.ts";

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
                {routes.map((route) => (
                    <div className="p-2" key={Math.random()}>
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
                                        <img className="w-8 h-8" src="/img/rate_review.svg"/>
                                    </button>
                                    <button className="p-1">
                                        <img className="w-8 h-8" src="/img/reviews.svg"/>
                                    </button>
                                    <button className="p-1">
                                        <img className="w-8 h-8" src="/img/favorite_border.svg"/>
                                    </button>
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
                    </div>
                ))}
                </div>
            </div>
            <div className="flex my-2 align-center justify-center w-full">
                {currentPage > 0 && (
                    <button className="px-4 py-3 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all rounded-lg"
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
