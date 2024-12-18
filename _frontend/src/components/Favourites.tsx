import React, {useEffect, useState} from 'react'
import {RouteDto} from "../api/dto.ts";
import {fetchFavouriteRoutes} from "../api/DirectoryApi.ts";

const Favourites = () => {
    const [routes, setRoutes] = useState<RouteDto[]>([])

    useEffect(() => {
        const fetchData = async () => {
            const favouriteRoutes = await fetchFavouriteRoutes(); // Выполняем запрос
            console.log(favouriteRoutes)
            setRoutes(favouriteRoutes);
        }
        fetchData();
    }, []);
    
    return (
        <div className="min-w-full flex flex-col h-full">
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
                                        <p>Примерный интервал в
                                            минутах: {route.frequencyRangeStart} - {route.frequencyRangeEnd}</p>
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
            </div>
        </div>
    )
}
export default Favourites
