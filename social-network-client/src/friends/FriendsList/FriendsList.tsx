import React from "react";
import {IUser} from "../../models/user";
import InfiniteScroll from "react-infinite-scroll-component";
import {FriendsListItem} from "../FriendsListItem";
import {List} from "semantic-ui-react";
import "./FriendsList.scss";

export interface UsersListProps {
    users: IUser[]
    hasMore: boolean;
    loadMore: () => void;
    isFriend: boolean;
}

export const FriendsList = ({users, hasMore, loadMore, isFriend}: UsersListProps) => {
    return (
        <InfiniteScroll
            next={loadMore}
            hasMore={hasMore}
            loader={<h4>Loading...</h4>}
            dataLength={users.length}
        >
            <List relaxed="very" divided>
                {users.length
                    ? users.map((user) => (
                        <FriendsListItem user={user} key={user.id} isFriend={isFriend}/>
                    ))
                    : <List.Item className="no-user-found">Ни одного друга не найдено</List.Item>
                }
            </List>
        </InfiniteScroll>
    );
};