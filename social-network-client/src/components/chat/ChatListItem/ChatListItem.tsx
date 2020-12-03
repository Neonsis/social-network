import React from 'react';
import "./ChatListItem.scss";
import {Image, List} from "semantic-ui-react";
import {Link} from "react-router-dom";
import {IUser} from "../../../models/user";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import {cropImage} from "../../../util/image";

export interface ChatListItemProps {
    user: IUser;
}

export const ChatListItem = ({user}: ChatListItemProps) => {
    return (
        <List.Item className="chat-list__item active" as={Link} to={`/chats/${user.id}`}>
            <Image avatar src={user.avatar ? cropImage(user.avatar.originalUrl) : AvatarNotFound} size="mini"/>
            <List.Content>
                <List.Header>{user.lastName} {user.firstName}</List.Header>
                <List.Description>
                    TEST
                </List.Description>
            </List.Content>
        </List.Item>
    );
};