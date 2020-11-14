import axios, {AxiosResponse} from "axios";
import {IUser, IUserAuth, IUserDetails, IUserFormValues} from "../models/user";
import {IProfileDetails} from "../models/profile";
import {Page} from "../models/page";
import {IPost, IPostFormValues} from "../models/post";

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
    login: (user: IUserFormValues): Promise<IUserAuth> => requests.post("/auth/signin", user),
    logout: (): Promise<void> => requests.post("/auth/logout", {}),
    signup: (user: IUserFormValues): Promise<IUserAuth> => requests.post("/auth/signup", user),
    current: (): Promise<IUser> => requests.get("/users/me"),
    get: (userId: string): Promise<IUserDetails> => requests.get(`/users/${userId}`),
    profileDetails: (userId: string): Promise<IProfileDetails> => requests.get(`/profiles/${userId}`),
    saveDetails: (details: IProfileDetails): Promise<void> => requests.put(`/profiles`, details),
}

const Friendship = {
    getFriends: (userId: string, page: number, size: number)
        : Promise<Page<IUser[]>> => requests.get(`/friends/${userId}?size=${size}&page=${page}`),
    post: (friendId: string): Promise<void> => requests.post(`/friends/${friendId}`, {}),
    delete: (friendId: string): Promise<void> => requests.del(`/friends/${friendId}`)
}

const Post = {
    create: (post: IPostFormValues): Promise<IPost> => requests.post(`/posts/`, post)
}

export default {
    User,
    Friendship,
    Post
};