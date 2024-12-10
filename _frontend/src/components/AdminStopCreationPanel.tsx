import React, {useState} from "react";
import Input from "../ui/Input.tsx";
import {addStopReq, fetchCitiesByRegionCode} from "../api/AdminApi.ts";
import {CityDto} from "../api/dto.ts";

interface AdminCityCreationPanelProps {
    showNotification: (message: string) => void,
    showErrorNotification: (message: string) => void
}

const AdminCityCreationPanel = ({showNotification, showErrorNotification}: AdminCityCreationPanelProps) => {
    const [stopRegionCode, setStopRegionCode] = useState<number | null>(null)
    const [stopCities, setStopCities] = useState<CityDto[]>([])
    const [stopCityId, setStopCityId] = useState<bigint | null>(null)
    const [stopName, setStopName] = useState<string>("")

    const fetchStopCitiesByRegionCode = async () => {
        if (stopRegionCode === null || stopRegionCode === undefined) {
            console.warn("Код региона не указан");
            return;
        }

        fetchCitiesByRegionCode(stopRegionCode)
            .then(res => {setStopCities(res)})
            .catch(err => showErrorNotification(err.response.data.message));
    };

    const addStop = () => {
        if (stopCityId === null || stopCityId < 0) {
            showNotification("Выберите город");
            return;
        }

        if (stopName === null || stopName.length === 0) {
            showNotification("Введите корректное название остановки");
            return;
        }

        addStopReq({cityId: stopCityId, name: stopName})
            .then(res => showNotification(res))
            .catch(err => showErrorNotification(err.response.data.message));
    }

    return (
        <div className="flex flex-col mx-2 border my-4 rounded-lg shadow-md overflow-hidden">
            <h1 className="font-bold p-2 border">Добавить новую остановку</h1>
            <Input type={"number"} value={stopRegionCode ?? ''} placeholder={"Введите код региона"}
                   onChange={(value) => setStopRegionCode(value as number)}/>
            <select
                className="border bg-white border-gray-200 p-2 rounded"
                onFocus={fetchStopCitiesByRegionCode}
                onChange={(e) => setStopCityId(e.target.value as unknown as bigint)}
                value={stopCityId?.toString()}>
                <option value="">Выберите город</option>
                {stopCities.map((city) => (
                    <option key={city.id} value={city.id.toString()}>
                        {city.name}
                    </option>
                ))}
            </select>
            <Input type={"text"} value={stopName ?? ''} placeholder={"Введите название остановки"}
                   onChange={(value) => setStopName(value as string)}/>
            <button
                className="px-4 py-3 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all"
                onClick={addStop}>
                Добавить новую остановку
            </button>
        </div>
    )
}
export default AdminCityCreationPanel
