import React, {useState} from 'react'
import Header from "./components/Header.tsx";
import {Component} from "./enums/Component.tsx";
import Directory from "./components/Directory.tsx";
import Favourites from "./components/Favourites.tsx";
import Login from "./components/Login.tsx";
import AdminPanel from "./components/AdminPanel.tsx";

const App = () => {

    const [selectedComponent, setSelectedComponent] = useState<Component | null>(null);

    const handleComponentSelect = (component: Component) => {
        setSelectedComponent(component);
    };

    const renderComponent = () => {
        switch (selectedComponent) {
            case Component.Directory:
                return <Directory />;
            case Component.Favourites:
                return <Favourites />;
            case Component.Login:
                return <Login />;
            case Component.AdminPanel:
                return <AdminPanel />;
            default:
                return <></>;
        }
    };

    return (
        <div className="relative overflow-x-hidden overflow-y-hidden flex flex-col items-center align-middle min-h-screen w-screen bg-gradient-to-r from-greyGreen-50 to-greyGreen-200">
            <Header onSelectComponent={handleComponentSelect} selectedComponent={selectedComponent} />
            {selectedComponent && (
                <div className="flex my-2 mx-12 w-full max-w-screen-lg  flex-col rounded-2xl bg-white">
                    <div className="flex flex-row-reverse">
                        <button
                            onClick={() => setSelectedComponent(null)}
                            className="bg-red-900 text-white rounded-2xl p-3 m-2">
                            Закрыть
                        </button>
                        {selectedComponent != Component.AdminPanel ? (
                            <button
                                onClick={() => setSelectedComponent(Component.AdminPanel)}
                                className="bg-green-900 text-white rounded-2xl p-3 m-2">
                                Панель администратора
                            </button>
                        ) : (
                            <button
                                onClick={() => setSelectedComponent(Component.Directory)}
                                className="bg-green-900 text-white rounded-2xl p-3 m-2">
                                Назад
                            </button>
                        )}
                    </div>
                    <div
                        style={{
                            height: "calc(100vh - 300px)"
                        }}>
                        {renderComponent()}
                    </div>
                </div>
            )}
        </div>
    )
}
export default App
