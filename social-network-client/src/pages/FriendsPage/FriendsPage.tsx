import React, {SyntheticEvent, useContext, useEffect, useState} from "react";
import {Grid, Input, Menu, Segment} from "semantic-ui-react";
import {observer} from "mobx-react-lite";
import {RouteComponentProps} from "react-router";
import "./FriendsPage.scss";
import {RootStoreContext} from "../../stores/rootStore";
import {FriendsList} from "../../friends/FriendsList";
import {Link} from "react-router-dom";

export const FriendsPage = observer(({location}: RouteComponentProps) => {
    const rootStore = useContext(RootStoreContext);
    const {
        user
    } = rootStore.userStore;
    const {
        loadFriends,
        loadFollowers,
        fetchMoreFriends,
        fetchMoreFollowers,
        friends,
        followers,
        isLastFriends,
        isLastFollowers
    } = rootStore.friendsStore;
    const [searchValue, setSearchValue] = useState("");

    let urlSearchParams = new URLSearchParams(location.search);

    const isFollowersPage = urlSearchParams.get("section") === "followers";
    const userId = urlSearchParams.get("id") ? urlSearchParams.get("id") : user!.id;

    useEffect(() => {
        if (isFollowersPage) {
            loadFollowers(userId!);
        } else {
            loadFriends(userId!, searchValue);
        }
    }, [userId, isFollowersPage, searchValue])

    return (
        <Grid className="friends-page">
            <Grid.Column width={11}>
                <Segment>
                    <Menu pointing secondary>
                        <Menu.Item
                            name="Все друзья"
                            active
                        />
                    </Menu>
                    {!isFollowersPage && <Input
                        fluid
                        icon="search"
                        iconPosition="left"
                        placeholder="Поиск друзей"
                        transparent
                        className="friends-search"
                        value={searchValue}
                        onChange={(event: SyntheticEvent<HTMLInputElement>) => setSearchValue(event.currentTarget.value)}
                    />}
                    {isFollowersPage
                        ? <FriendsList
                            isFriend={false}
                            users={followers}
                            loadMore={() => fetchMoreFollowers(user!.id)}
                            hasMore={!isLastFollowers}
                        />
                        : <FriendsList
                            isFriend={true}
                            users={friends}
                            loadMore={() => fetchMoreFriends(user!.id, searchValue)}
                            hasMore={!isLastFriends}
                        />
                    }
                </Segment>
            </Grid.Column>
            <Grid.Column width={5} className="friends-menu">
                {userId === user!.id && <Menu vertical>
                    <Menu.Item
                        name="friends"
                        active={!isFollowersPage}
                        as={Link}
                        to={`friends?id=${userId}`}
                    >
                        Мои друзья
                    </Menu.Item>
                    <Menu.Item
                        name="friends"
                        as={Link}
                        active={isFollowersPage}
                        to={`friends?id=${userId}&section=followers`}
                    >
                        Заявки в друзья
                    </Menu.Item>
                </Menu>}
            </Grid.Column>
        </Grid>
    );
});