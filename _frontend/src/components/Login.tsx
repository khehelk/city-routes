import React, {useState} from 'react'
import Input from "../ui/Input.tsx";
import {loginReq} from "../api/AuthApi.ts";

const Login = () => {
    const [email, setEmail] = useState<string>("")
    const [password, setPassword] = useState<string>("")
    const [notification, setNotification] = useState<string | null>(null);
    const [errorNotification, setErrorNotification] = useState<string | null>(null);

    const login = () => {
        if (!email) {
            showNotification("Введите корректную почту");
            return;
        }

        if (!password) {
            showNotification("Введите корректный пароль");
            return;
        }

        loginReq({email: email, password: password})
            .then(res => {
                localStorage.setItem("token", res)
                showNotification("Успешный вход")
                window.location.reload()
            })
            .catch(err => showErrorNotification(err.response.data.message));
    }

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

    return (

        <div className="flex flex-col justify-center">
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
            <div className="m-2 flex flex-col justify-center">
                <h1 className="p-2 font-bold border flex justify-center rounded-t-2xl">Вход</h1>
                <div className="flex justify-center">
                    <Input key={"email"} type={"text"} value={email ?? ''} placeholder={"Введите email"}
                           onChange={(value) => setEmail(value as string)}/>
                </div>
                <div className="flex justify-center">
                    <Input key={"password"} type={"password"} value={password ?? ''} placeholder={"Введите пароль"}
                           onChange={(value) => setPassword(value as string)}/>
                </div>
                <button
                    onClick={login}
                    className="px-4 py-3 rounded-b-2xl bg-greyGreen-50 text-white font-semibold hover:bg-greyGreen-200 transition-all">
                    Войти
                </button>
            </div>
        </div>
    )
}
export default Login

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