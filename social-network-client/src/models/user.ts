export interface IUser {
    id: string;
    firstName: string;
    lastName: string;
    token: string;
    avatarUrl?: string;
}

export interface IUserFormValues {
    email: string;
    password: string;
    gender: "MALE" | "FEMALE";
    birthday: Date;
    firstName?: string;
    lastName?: string;
}