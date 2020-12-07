import React, {useContext, useEffect} from 'react';
import {observer} from "mobx-react-lite";
import {RouteComponentProps} from "react-router";
import {Button, Dropdown, Grid, Header, Image, Segment} from "semantic-ui-react";
import {cropImage} from "../../util/image";
import GroupNotFound from "../../assets/group_not_found.png";
import {RootStoreContext} from "../../stores/rootStore";
import "./GroupPage.scss";
import {PostCreateForm} from "../../components/posts";
import {GroupPostsList} from "../../components/posts/GroupPostsList";
import {ProfilesSection} from "../../components/profiles";
import {UserAvatar} from "../../components/display";

interface GroupPageProps {
    groupId: string;
}

export const GroupPage = observer<RouteComponentProps<GroupPageProps>>(({match}) => {

    const groupId = match.params.groupId;
    const rootStore = useContext(RootStoreContext);
    const {
        user
    } = rootStore.userStore;
    const {
        group,
        loadGroup,
        follow,
        unfollow,
        groupLoader,
        followLoader,
        loadGroupFollowers,
        groupFollowers,
        groupFollowersLoader
    } = rootStore.groupsStore;
    const {
        createPost,
        saveLoadingPost,
    } = rootStore.groupPostStore;

    const isAdmin = group?.moderator.id === user?.id;

    const unfollowButton = () => {
        return (
            <Dropdown
                button
                text="Вы подписаны"
                className="group__button secondary-button"
                disabled={followLoader}
                loading={followLoader}
            >
                <Dropdown.Menu className="profile__dropdown">
                    <Dropdown.Item
                        className="profile__dropdown-item"
                        onClick={() => unfollow(groupId)}
                    >
                        Отписаться
                    </Dropdown.Item>
                </Dropdown.Menu>
            </Dropdown>
        )
    };

    const followButton = () => (
        <Button
            className="group__button primary-button"
            onClick={() => follow(groupId)}
            disabled={followLoader}
            loading={followLoader}
        >
            Подписаться
        </Button>
    );

    useEffect(() => {
        loadGroup(groupId);
        loadGroupFollowers(groupId);
    }, [groupId])

    if (groupLoader) {
        return null;
    }

    return (
        <div>
            <Segment className="group-header-bar">
                <Image
                    className="group-avatar"
                    avatar
                    src={group?.avatar ? cropImage(group.avatar.originalUrl) : GroupNotFound}
                />
                <Header className="group-header">{group?.title}</Header>
                {!isAdmin && (group?.isUserFollow
                        ? unfollowButton()
                        : followButton()
                )}
            </Segment>
            <Grid>
                <Grid.Column width={11}>
                    {isAdmin && <PostCreateForm
                        loading={saveLoadingPost}
                        create={(post) => createPost(post, groupId)}
                    />}
                    <GroupPostsList groupId={groupId}/>
                </Grid.Column>
                <Grid.Column width={5}>
                    <ProfilesSection
                        userId={user?.id}
                        header="Подписчики"
                        profiles={groupFollowers!}
                        loading={groupFollowersLoader}
                    />
                    <Segment>
                        <Header as="h4">Администратор</Header>
                        <UserAvatar key={group?.moderator.id} user={group?.moderator!}/>
                    </Segment>
                </Grid.Column>
            </Grid>
        </div>
    );
});