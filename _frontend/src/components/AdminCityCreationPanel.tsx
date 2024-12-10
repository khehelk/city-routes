import React, {useState} from 'react'
import Input from "../ui/Input.tsx";
import {addCityReq} from "../api/AdminApi.ts";

interface AdminCityCreationPanelProps {
    showNotification: (message: string) => void,
    showErrorNotification: (message: string) => void
}

const AdminCityCreationPanel = ({showNotification, showErrorNotification}: AdminCityCreationPanelProps) => {
    const [cityRegionCode, setCityRegionCode] = useState<number | null>(null)
    const [cityName, setCityName] = useState<string>("")

    const addCity = () => {
        if (cityRegionCode === null || cityRegionCode < 0) {
            showNotification("Введите корректный код региона");
            return;
        }

        if (cityName === null || cityName.length === 0) {
            showNotification("Введите корректное название города");
            return;
        }

        addCityReq({regionCode: cityRegionCode, name: cityName})
            .then(res => showNotification(res))
            .catch(err => showErrorNotification(err.response.data.message));
    }

    return (
        <div className="flex flex-col mx-2 border rounded-lg shadow-md overflow-hidden">
            <h1 className="p-2 font-bold border">Добавить город</h1>
            <Input key={"cityCode"} type={"number"} value={cityRegionCode ?? ''} placeholder={"Введите код региона"}
                   onChange={(value) => setCityRegionCode(value as number)}/>
            <Input key={"cityName"} type={"text"} value={cityName ?? ''} placeholder={"Введите название города"}
                   onChange={(value) => setCityName(value as string)}/>
            <button
                className="px-4 py-3 bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all"
                onClick={addCity}>
                Добавить новый город
            </button>
        </div>
    )
}
export default AdminCityCreationPanel
