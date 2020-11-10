import React, {useContext} from 'react';
import {Button, Dropdown, Image, Segment} from "semantic-ui-react";
import NotFoundAvatar from "../../../assets/avatar_not_found.png";
import "./ProfileAvatar.scss";
import {Link} from "react-router-dom";
import {RootStoreContext} from "../../../stores/rootStore";
import {observer} from "mobx-react-lite";

export interface ProfileAvatarProps {
    avatarUrl?: string;
    id: string;
    isFriend: boolean;
    isLoggedInUser: boolean;
    isFollower: boolean;
    isPendingFriendship: boolean;
}

export const ProfileAvatar = observer<ProfileAvatarProps>(({
                                                               avatarUrl,
                                                               isLoggedInUser,
                                                               isPendingFriendship,
                                                               isFriend,
                                                               isFollower,
                                                               id
                                                           }) => {

    const rootStore = useContext(RootStoreContext);
    const {
        deleteFriendship,
        addToFriends,
        cancelFriendship,
        confirmFriendship,
        loading
    } = rootStore.friendshipStore;

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

    const addToFriendsButton = () => {
        const handleAddToFriends = () => {
            if (isFollower) {
                confirmFriendship(id);
            } else {
                addToFriends(id);
            }
        }

        return (
            <Button
                fluid
                className="profile__button primary-button"
                onClick={handleAddToFriends}
                loading={loading}
                disabled={loading}
            >
                Добавить в друзья
            </Button>
        )
    };

    const editButton = () => (
        <Button fluid className="profile__button secondary-button">Редактировать</Button>
    );

    const pendingButton = () => {
        const handleFriendshipDelete = () => {
            cancelFriendship(id);
        };

        return (
            <Dropdown
                fluid
                button
                text='Заявка отправлена'
                className="profile__button secondary-button"
                loading={loading}
                disabled={loading}
            >
                <Dropdown.Menu className="profile__dropdown">
                    <Dropdown.Item className="profile__dropdown-item" onClick={handleFriendshipDelete}>
                        Отменить отправку
                    </Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        )
    };

    const friendButton = () => {
        const handleFriendshipDelete = () => {
            deleteFriendship(id);
        };

        return (
            <Dropdown
                fluid
                button
                text='У вас в друзьях'
                className="profile__button secondary-button"
                loading={loading}
                disabled={loading}
            >
                <Dropdown.Menu className="profile__dropdown">
                    <Dropdown.Item className="profile__dropdown-item" onClick={handleFriendshipDelete}>
                        Убрать из друзей
                    </Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        )
    };

    return (
        <Segment className="profile__avatar-container">
            <Image className="profile__avatar" src={avatarUrl ? avatarUrl : NotFoundAvatar}/>
            <div className="profile__buttons">
                {isLoggedInUser && editButton()}
                {!isLoggedInUser && writeMessageButton()}
                {!isLoggedInUser && !isFriend && !isPendingFriendship && addToFriendsButton()}
                {isPendingFriendship && pendingButton()}
                {isFriend && friendButton()}
                {isFollower && <div className="profile__avatar-follower">Пользователь подписан на Вас</div>}
            </div>
        </Segment>
    );
});