import React from "react";
import {Image} from "semantic-ui-react";
import {IUser} from "../../../models/user";
import {Link} from "react-router-dom";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import "./UserAvatar.scss";

export interface UserAvatarProps {
    user: IUser
}

export const UserAvatar = ({user}: UserAvatarProps) => {
    return (
        <div className="profile__section-item">
            <Link className="profile__item-avatar-wrapper" to={`/id${user.id}`}>
                <Image className="profile__item-avatar"
                       avatar
                       src={user.avatarUrl ? user.avatarUrl : AvatarNotFound}
                />
                <div>{user.firstName}</div>
            </Link>
        </div>
    );
};