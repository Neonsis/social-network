import React from 'react';
import {Button, Dropdown, Image, Segment} from "semantic-ui-react";
import NotFoundAvatar from "../../../assets/avatar_not_found.png";
import "./ProfileAvatar.scss";
import {Link} from "react-router-dom";

export interface ProfileAvatarProps {
    avatarUrl?: string;
    isFriend: boolean;
    isLoggedInUser: boolean;
    isPendingFriendship: boolean;
}

export const ProfileAvatar = ({avatarUrl, isLoggedInUser, isPendingFriendship, isFriend}: ProfileAvatarProps) => {

    const writeMessageButton = () => (
        <Button
            fluid
            className="profile__button primary-button"
            as={Link}
            to="/test"
        >
            Написать сообщение
        </Button>
    );

    const addToFriendsButton = () => (
        <Button
            fluid
            className="profile__button primary-button"
            as={Link}
            to="/test"
        >
            Добавить в друзья
        </Button>
    );

    const editButton = () => (
        <Button fluid className="profile__button secondary-button">Редактировать</Button>
    );

    const pendingButton = () => (
        <Dropdown
            fluid
            button
            text='Заявка отправлена'
            className="profile__button secondary-button"
        >
            <Dropdown.Menu className="profile__dropdown">
                <Dropdown.Item className="profile__dropdown-item">Отменить отправку</Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
    );

    const friendButton = () => (
        <Dropdown
            fluid
            button
            text='У вас в друзьях'
            className="profile__button secondary-button"
        >
            <Dropdown.Menu className="profile__dropdown">
                <Dropdown.Item className="profile__dropdown-item">Убрать из друзей</Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
    );

    return (
        <Segment className="profile__avatar-container">
            <Image className="profile__avatar" src={avatarUrl ? avatarUrl : NotFoundAvatar}/>
            <div className="profile__buttons">
                {isLoggedInUser && editButton()}
                {!isLoggedInUser && writeMessageButton()}
                {!isLoggedInUser && !isFriend && !isPendingFriendship && addToFriendsButton()}
                {isPendingFriendship && pendingButton()}
                {isFriend && friendButton()}
            </div>
        </Segment>
    );
};