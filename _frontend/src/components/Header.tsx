import React from 'react'
import {Component} from "../enums/Component.tsx";
import {getUserRoles} from "../utils/DecodedToken.ts";

interface HeaderProps {
    onSelectComponent: (component: Component) => void;
    selectedComponent: Component | null;
}

const Header = (props: HeaderProps) => {
    return (
        <div className={`${props.selectedComponent == null 
            ? `h-screen w-screen` 
            : ``} flex-col  items-center align-center justify-center flex overflow-x-hidden overflow-y-hidden`}>
            <h1 className="text-8xl text-white font-bold font-roboto uppercase ">City Routes</h1>
            <nav className={`${props.selectedComponent == null
                ? `flex-col`
                : `flex-row`} flex`}>
                <button
                    className="text-4xl m-1 px-4 py-2 flex justify-center bg-transparent text-white rounded hover:bg-white hover:text-greyGreen-50 transition"
                    onClick={() => props.onSelectComponent(Component.Directory)}>
                    Справочник
                </button>
                {getUserRoles().length !== 0 &&
                    <>
                        <button
                            className="text-4xl m-1 px-4 py-2 flex justify-center bg-transparent text-white rounded hover:bg-white hover:text-greyGreen-50 transition"
                            onClick={() => props.onSelectComponent(Component.Favourites)}>
                            Избранные
                        </button>
                        <button
                            className="text-4xl m-1 px-4 py-2 flex justify-center bg-transparent text-white rounded hover:bg-white hover:text-greyGreen-50 transition"
                            onClick={() => {
                                localStorage.clear()
                                window.location.reload()
                            }}>
                            Выход
                        </button>
                    </>
                }
                {getUserRoles().length === 0 &&
                    <button
                        className="text-4xl m-1 px-4 py-2 flex justify-center bg-transparent text-white rounded hover:bg-white hover:text-greyGreen-50 transition"
                        onClick={() => props.onSelectComponent(Component.Login)}>
                        Вход
                </button>}
                {getUserRoles().length === 0 &&
                <button
                    className="text-4xl m-1 px-4 py-2 flex justify-center bg-transparent text-white rounded hover:bg-white hover:text-greyGreen-50 transition"
                    onClick={() => props.onSelectComponent(Component.Registration)}>
                    Регистрация
                </button>}
            </nav>
        </div>
    )
}
export default Header
