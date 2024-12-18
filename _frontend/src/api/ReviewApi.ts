import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8083/api/v1",
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

export const sendCommentReq = async (comment: string, routeId: bigint): Promise<string> => {
    const response = await api.post(`/reviews`, {comment: comment, routeId: routeId});
    console.log(response);
    return response.data;
}

export const fetchComments = async (id: bigint, offset: bigint): Promise<{author: string, comment: string, date: Date}[]> => {
    const response = await api.get(`/reviews/${id}?limit=${offset}`);
    console.log(response);
    return response.data;
}
