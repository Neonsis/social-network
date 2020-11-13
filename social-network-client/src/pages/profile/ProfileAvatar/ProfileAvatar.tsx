import React from "react";
import {Button, Dropdown, Image, Segment} from "semantic-ui-react";
import NotFoundAvatar from "../../../assets/avatar_not_found.png";
import "./ProfileAvatar.scss";
import {Link} from "react-router-dom";
import ContentLoader from "react-content-loader";

export interface ProfileAvatarProps {
    avatarUrl?: string;
    id: string;
    isFriend?: boolean;
    isLoggedInUser?: boolean;
    isFollower?: boolean;
    isPendingFriendship?: boolean;
    loadingPage: boolean;
    confirmFriendship: (id: string) => void;
    addToFriends: (id: string) => void;
    cancelFriendship: (id: string) => void;
    deleteFriendship: (id: string) => void;
}

export const ProfileAvatar = (({
                                   avatarUrl,
                                   isLoggedInUser,
                                   isPendingFriendship,
                                   isFriend,
                                   isFollower,
                                   id,
                                   loadingPage,
                                   confirmFriendship,
                                   addToFriends,
                                   cancelFriendship,
                                   deleteFriendship
                               }: ProfileAvatarProps) => {

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
                loading={loadingPage}
                disabled={loadingPage}
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
                text="Заявка отправлена"
                className="profile__button secondary-button"
                loading={loadingPage}
                disabled={loadingPage}
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
                text="У вас в друзьях"
                className="profile__button secondary-button"
                loading={loadingPage}
                disabled={loadingPage}
            >
                <Dropdown.Menu className="profile__dropdown">
                    <Dropdown.Item className="profile__dropdown-item" onClick={handleFriendshipDelete}>
                        Убрать из друзей
                    </Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        )
    };

    if (loadingPage) return (
        <Segment className="profile__avatar-container">
            <ContentLoader
                speed={2}
                width={199}
                height={287}
                viewBox="0 0 199 287"
                backgroundColor="#f3ecec"
                foregroundColor="#ffffff"
            >
                <rect x="0" y="0" rx="0" ry="0" width="199" height="199"/>
                <rect x="1" y="215" rx="3" ry="3" width="199" height="31"/>
                <rect x="2" y="254" rx="3" ry="3" width="199" height="31"/>
            </ContentLoader>
        </Segment>
    )

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