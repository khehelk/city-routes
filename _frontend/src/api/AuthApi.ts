import axios from "axios";
import {LoginDto, RegistrationDto} from "./dto.ts";

const api = axios.create({
    baseURL: "http://localhost:8082/api/v1/auth",
});

export const registrationReq = async (data: RegistrationDto): Promise<string> => {
    const response = await api.post(`/register`, data);
    console.log(response);
    return response.data.token;
}

export const loginReq = async (data: LoginDto): Promise<string> => {
    const response = await api.post(`/login`, data);
    console.log(response);
    return response.data.token;
}