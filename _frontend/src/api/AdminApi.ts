import axios from 'axios';
import {CityCreationDto, CityDto, RouteCreationDto, RouteUpdateDto, StopCreationDto, StopDto} from "./dto.ts";

const api = axios.create({
    baseURL: "http://localhost:8080/api/v1"
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

export const addCityReq = async (data: CityCreationDto): Promise<string> => {
  const response = await api.post(`/cities`, data);
  return response.data;
};

export const addStopReq = async (data: StopCreationDto): Promise<string> => {
    const response = await api.post(`/stops`, data);
    console.log(response);
    return response.data;
};

export const addRouteReq = async (data: RouteCreationDto): Promise<string> => {
    console.log(data)
    const stopsArray = Object.fromEntries(data.stops.entries());
    const dataSerialized = {
        ...data,
        stops: stopsArray
    };
    const response = await api.post(`/routes`, JSON.stringify(dataSerialized), {
        headers: {
            'Content-Type': 'application/json',
        },
    });
    return response.data;
}

export const fetchCitiesByRegionCode = async (regionCode: number): Promise<CityDto[]> => {
    const response = await api.get(`/cities/in-region/${regionCode}`);
    console.log(response);
    return response.data;
}

export const fetchStopsByCityId = async (cityId: bigint): Promise<StopDto[]> => {
    const response = await api.get(`/stops?city_id=${cityId}`);
    return response.data;
};

export const updateRouteReq = async (id: bigint, data: RouteUpdateDto): Promise<string> => {
    const stopsArray = Object.fromEntries(data.stops.entries());
    const dataSerialized = {
        ...data,
        stops: stopsArray
    };
    console.log(dataSerialized);
    const response = await api.patch(`/routes/${id}`, JSON.stringify(dataSerialized), {
        headers: {
            'Content-Type': 'application/json',
        },
    });
    console.log(response);
    return response.data;
}

export const deleteRouteReq = async (id: bigint): Promise<string> => {
    const response = await api.delete(`/routes/${id}`);
    return response.data;
}