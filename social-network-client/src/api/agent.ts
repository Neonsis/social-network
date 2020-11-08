import axios, {AxiosResponse} from "axios";
import {IUser, IUserFormValues} from "../models/user";

axios.defaults.baseURL = process.env.REACT_APP_API_URL;

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

const user = {
    login: (user: IUserFormValues): Promise<IUser> => requests.post("/auth/signin", user),
    signup: (user: IUserFormValues): Promise<IUser> => requests.post("/auth/signup", user)
}

export {
    user
};