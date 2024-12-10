import React, {useState} from 'react'
import AdminCityCreationPanel from "./AdminCityCreationPanel.tsx";
import AdminStopCreationPanel from "./AdminStopCreationPanel.tsx";
import AdminRouteCreationPanel from "./AdminRouteCreationPanel.tsx";

const AdminPanel = () => {
    const [notification, setNotification] = useState<string | null>(null);
    const [errorNotification, setErrorNotification] = useState<string | null>(null);
    
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
        <div className="min-w-full admin-panel rounded-2xl overflow-y-auto">
            <AdminCityCreationPanel showNotification={showNotification} showErrorNotification={showErrorNotification} />
            <AdminStopCreationPanel showNotification={showNotification} showErrorNotification={showErrorNotification} />
            <AdminRouteCreationPanel showNotification={showNotification} showErrorNotification={showErrorNotification} />
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

export default AdminPanel
