import React, {useState} from 'react'
import Input from "../ui/Input.tsx";
import {addRouteReq, fetchCitiesByRegionCode, fetchStopsByCityId} from "../api/AdminApi.ts";
import {CityDto, StopDto} from "../api/dto.ts";

interface AdminCityCreationPanelProps {
    showNotification: (message: string) => void,
    showErrorNotification: (message: string) => void
}

const AdminCityCreationPanel = ({showNotification, showErrorNotification}: AdminCityCreationPanelProps) => {
    const [routeRegionCode, setRouteRegionCode] = useState<number | null>(null)
    const [routeCities, setRouteCities] = useState<CityDto[]>([])
    const [routeNumber, setRouteNumber] = useState<number | null>(null)
    const [frequencyRangeStart, setFrequencyRangeStart] = useState<number | null>(null)
    const [frequencyRangeEnd, setFrequencyRangeEnd] = useState<number | null>(null)
    const [routeCityId, setRouteCityId] = useState<bigint | null>(null)
    const [routeAllStops, setRouteAllStops] = useState<StopDto[]>([])
    const [routeSelectedStops, setRouteSelectedStops] = useState<StopDto[]>([])

    const fetchRouteCitiesByRegionCode = async () => {
        if (routeRegionCode === null || routeRegionCode === undefined) {
            console.warn("Код региона не указан");
            return;
        }

        fetchCitiesByRegionCode(routeRegionCode)
            .then(res => {setRouteCities(res)})
            .catch(err => showErrorNotification(err.response.data.message));
    }

    const getStopsByCityId = () => {
        if (routeCityId === null || routeCityId < 0) {
            showNotification("Введите корректный код региона");
            return;
        }

        fetchStopsByCityId(routeCityId)
            .then(res => {setRouteAllStops(res)})
            .catch(err => showErrorNotification(err.response.data.message));
    }

    const addStopToRoute = (id: string) => {
        const stop = routeAllStops.find(stop => stop.id.toString() === id)
        if (stop === undefined || stop === null) {
            return;
        }
        if (!routeSelectedStops.some(stop => stop.id.toString() === id.toString() && stop.name === stop.name)) {
            setRouteSelectedStops((prevStops) => [...prevStops, stop]);
        }
    }

    const removeStop = (dto: StopDto) => {
        setRouteSelectedStops(routeSelectedStops.filter((s) => s !== dto));
    }

    const addRoute = () => {
        if (routeCityId === null || routeCityId < 0) {
            showNotification("Выберите город");
            return;
        }
        if (routeSelectedStops === null || routeSelectedStops.length === 0) {
            showNotification("Выберите остановки");
            return;
        }
        if (routeNumber === null || routeNumber < 0) {
            showNotification("Введите корректный номер маршрута");
            return;
        }
        if (frequencyRangeStart === null || frequencyRangeEnd === null || frequencyRangeStart > frequencyRangeEnd) {
            showNotification("Введите корректный временной промежуток");
            return;
        }

        addRouteReq({
            cityId: routeCityId,
            number: routeNumber,
            frequencyRangeStart: frequencyRangeStart,
            frequencyRangeEnd: frequencyRangeEnd,
            stops: new Map(routeSelectedStops.map((stop, index) => [index + 1, stop.id]))})
            .then(res => showNotification(res))
            .catch(err => showErrorNotification(err.response.data.message));
    }

    return (
        <div className="flex flex-col mx-2 border rounded-lg shadow-md overflow-hidden">
            <h1 className="p-2 font-bold border">Добавить новый маршрут</h1>
            <Input type={"number"} value={routeRegionCode ?? ''} placeholder={"Введите код региона"}
                   onChange={(value) => setRouteRegionCode(value as number)}/>
            <select
                className="border bg-white border-gray-200 p-2 rounded"
                onFocus={fetchRouteCitiesByRegionCode}
                onChange={(e) => setRouteCityId(e.target.value as unknown as bigint)}
                value={routeCityId?.toString()}>
                <option value="">Выберите город</option>
                {routeCities.map((city) => (
                    <option key={city.id} value={city.id.toString()}>
                        {city.name}
                    </option>
                ))}
            </select>
            <Input type={"number"} value={routeNumber ?? ''} placeholder={"Введите номер маршрута"}
                   onChange={(value) => setRouteNumber(value as number)}/>
            <Input type={"number"} value={frequencyRangeStart ?? ''}
                   placeholder={"Введите начальное значение диапазона времени ожидания (минуты)"}
                   onChange={(value) => setFrequencyRangeStart(value as number)}/>
            <Input type={"number"} value={frequencyRangeEnd ?? ''}
                   placeholder={"Введите конечное значение диапазона времени ожидания (минуты)"}
                   onChange={(value) => setFrequencyRangeEnd(value as number)}/>
            <select
                className="border bg-white border-gray-200 p-2 rounded"
                onFocus={getStopsByCityId}
                onChange={(e) => addStopToRoute(e.target.value)}
                value="">
                <option value="">Выберите остановку</option>
                {routeAllStops.map((stop) => (
                    <option key={stop.id} value={stop.id.toString()}>
                        {stop.name}
                    </option>
                ))}
            </select>
            <div className="flex overflow-x-auto space-x-4 border p-2 rounded">
                {routeSelectedStops.map((stop) => (
                    <div
                        key={stop.id}
                        className="flex items-center space-x-2 border px-4 py-2 rounded bg-gray-100">
                        <span>{stop.name}</span>
                        <button
                            className="text-red-500 font-bold"
                            onClick={() => removeStop(stop)}>
                            ×
                        </button>
                    </div>
                ))}
            </div>
            <button
                className="px-4 py-3 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all"
                onClick={addRoute}>
                Добавить новый маршрут
            </button>
        </div>
    )
}
export default AdminCityCreationPanel
