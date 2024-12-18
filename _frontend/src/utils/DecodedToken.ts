import {jwtDecode} from 'jwt-decode';

interface DecodedToken {
    roles: string[];
}

export const getUserRoles = (): string[] => {
    const token = localStorage.getItem('token');
    if (token) {
        const decoded: DecodedToken = jwtDecode(token);
        return decoded.roles;
    }
    return [];
};

export const isAdmin = (): boolean => {
    return ["ADMIN"].some((role) => getUserRoles().includes(role))
}
