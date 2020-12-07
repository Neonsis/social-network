import React, {useContext} from "react";
import {IUser} from "../../models/user";
import {Button, Image, List} from "semantic-ui-react";
import "./FriendsListItem.scss";
import {Link} from "react-router-dom";
import AvatarNotFound from "../../assets/avatar_not_found.png";
import {RootStoreContext} from "../../stores/rootStore";
import {cropImage} from "../../util/image";

export interface FriendsListItemProps {
    user: IUser;
    isFriend: boolean;
}

export const FriendsListItem = ({user, isFriend}: FriendsListItemProps) => {
    const rootStore = useContext(RootStoreContext);
    const {addToFriends} = rootStore.friendsStore;

    return (
        <List.Item className="friends-list__item">
            <Image avatar size="tiny" src={user.avatar ? cropImage(user.avatar.originalUrl) : AvatarNotFound}/>
            <List.Content>
                <List.Header as={Link} to={`/id${user.id}`}>{user.firstName} {user.lastName}</List.Header>
                <List.Description>
                    <List.Item className="write-message">
                        {isFriend
                            ? <Link to={`/chats/${user.id}`}>Написать сообщение</Link>
                            : <Button
                                fluid
                                className="profile__button primary-button"
                                onClick={() => addToFriends(user.id)}
                            >
                                Добавить в друзья
                            </Button>
                        }
                    </List.Item>
                </List.Description>
            </List.Content>
        </List.Item>
    );
};