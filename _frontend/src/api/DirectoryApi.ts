import axios from "axios";
import {CityDto, RouteDto, RoutesPageDto, StopDto} from "./dto.ts";

const api = axios.create({
    baseURL: "http://localhost:8081/api/v1",
});

export const fetchCitiesByRegionCode = async (regionCode: number): Promise<CityDto[]> => {
    const response = await api.get(`/cities/in-region/${regionCode}`);
    console.log(response);
    return response.data;
}

export const fetchRoutesByCityId = async (cityId: bigint, page: number): Promise<RoutesPageDto> => {
    console.log("current page = " + page);
    const response = await api.get(`/routes?city_id=${cityId}&page=${page}`);
    const transformedContent = response.data.content.map((route: RouteDto) => {
        const stopsMap = new Map<number, StopDto>(
            Object.entries(route.stops).map(([key, value]) => [
                Number(key),
                value
            ])
        );
        return { ...route, stops: stopsMap };
    });
    return { ...response.data, content: transformedContent };
}