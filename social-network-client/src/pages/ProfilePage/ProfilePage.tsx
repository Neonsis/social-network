import React, {useContext, useEffect} from "react";
import {Grid} from "semantic-ui-react";
import {RouteComponentProps} from "react-router";
import {RootStoreContext} from "../../stores/rootStore";
import {observer} from "mobx-react-lite";
import {ProfileAvatar, ProfileInfo, ProfilesSection} from "../../components/profiles";
import {PostCreateForm, UserPostsList} from "../../components/posts";
import {FollowSection} from "../../components/profiles/FollowSection";

interface ProfilePageProps {
    userId: string;
}

export const ProfilePage = observer<RouteComponentProps<ProfilePageProps>>(({match}) => {
    const rootStore = useContext(RootStoreContext);
    const {
        saveLoadingPost,
        createPost
    } = rootStore.postStore;
    const {
        loadingPage,
        user,
        loadUser,
        profileDetails,
        loadingProfileDetails,
        loadingGroups,
        profileGroups,
        loadUserGroups
    } = rootStore.profileStore;
    const {
        loadProfileFriends,
        profileFriends,
        loadingFriends
    } = rootStore.friendshipStore;
    const {loadProfileDetails} = rootStore.profileStore;

    useEffect(() => {
        const userId = match.params.userId;
        loadUser(userId);
        loadProfileFriends(userId)
        loadProfileDetails(userId);
        loadUserGroups(userId);
    }, [match.params.userId, loadUser, loadProfileFriends, loadProfileDetails, loadUserGroups])

    return (
        <Grid>
            <Grid.Column width={5}>
                <ProfileAvatar
                    loadingPage={loadingPage}
                    id={match.params.userId}
                    isFriend={user?.isFriend}
                    isLoggedInUser={user?.isLoggedInUser}
                    isPendingFriendship={user?.isPendingFriendship}
                    isFollower={user?.isFollower}
                    avatar={user?.avatar}
                />
                <ProfilesSection
                    userId={user?.id}
                    header="Друзья"
                    profiles={profileFriends!}
                    loading={loadingFriends}
                />
                <FollowSection
                    groups={profileGroups!}
                    loading={loadingGroups}
                />
            </Grid.Column>
            <Grid.Column width={11}>
                <ProfileInfo
                    firstName={user?.firstName}
                    lastName={user?.lastName}
                    profileDetails={profileDetails!}
                    loading={loadingProfileDetails}
                />
                {user?.isLoggedInUser && <PostCreateForm create={createPost} loading={saveLoadingPost}/>}
                <UserPostsList userId={match.params.userId}/>
            </Grid.Column>
        </Grid>
    );
});