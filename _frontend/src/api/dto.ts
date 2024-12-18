export interface CityCreationDto {
    regionCode: number;
    name: string;
}

export interface CityDto {
    id: bigint;
    regionCode: number;
    name: string;
}

export interface StopCreationDto {
    cityId: bigint;
    name: string;
}

export interface StopDto {
    id: bigint;
    cityId: bigint;
    name: string;
}

export interface RouteCreationDto {
    cityId: bigint;
    number: number;
    frequencyRangeStart: number;
    frequencyRangeEnd: number;
    stops: Map<number, bigint>
}

export interface RouteDto {
    id: bigint;
    number: number;
    frequencyRangeStart: number;
    frequencyRangeEnd: number;
    stops: Map<number, StopDto>;
    isFavourite: boolean;
}

export interface RoutesPageDto {
    content: RouteDto[];
    size: number;
    number: number;
    totalPages: number;
}

export interface RouteUpdateDto {
    frequencyRangeStart: number;
    frequencyRangeEnd: number;
    stops: Map<number, bigint>
}

export interface RegistrationDto {
    email: string;
    password: string;
}

export interface LoginDto {
    email: string;
    password: string;
}
