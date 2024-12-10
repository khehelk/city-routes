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
