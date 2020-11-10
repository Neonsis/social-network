import axios, {AxiosResponse} from "axios";
import {IUser, IUserDetails, IUserFormValues} from "../models/user";
import {IProfileDetails} from "../models/profile";

axios.defaults.baseURL = process.env.REACT_APP_API_URL;

axios.interceptors.request.use(
    config => {
        const token = window.localStorage.getItem("jwt");
        if (token) config.headers.Authorization = `Bearer ${token}`;
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

axios.interceptors.response.use(undefined, error => {
    throw error.response;
});

const responseBody = (response: AxiosResponse) => response.data;

const requests = {
    get: (url: string) =>
        axios
            .get(url)
            .then(responseBody),
    post: (url: string, body: {}) =>
        axios
            .post(url, body)
            .then(responseBody),
    put: (url: string, body: {}) =>
        axios
            .put(url, body)
            .then(responseBody),
    del: (url: string) =>
        axios
            .delete(url)
            .then(responseBody)
}

const User = {
    login: (user: IUserFormValues): Promise<IUser> => requests.post("/auth/signin", user),
    logout: (): Promise<void> => requests.post("/auth/logout", {}),
    signup: (user: IUserFormValues): Promise<IUser> => requests.post("/auth/signup", user),
    current: (): Promise<IUser> => requests.get("/users/me"),
    get: (userId: string): Promise<IUserDetails> => requests.get(`/users/${userId}`),
    profileDetails: (userId: string): Promise<IProfileDetails> => requests.get(`/profiles/${userId}`)
}

const Friendship = {
    post: (friendId: string): Promise<void> => requests.post(`/friends/${friendId}`, {}),
    delete: (friendId: string): Promise<void> => requests.del(`/friends/${friendId}`)
}

export default {
    User,
    Friendship
};