import React, {useContext, useEffect} from "react";
import {Grid} from "semantic-ui-react";
import {RouteComponentProps} from "react-router";
import {RootStoreContext} from "../../stores/rootStore";
import {observer} from "mobx-react-lite";
import {ProfileAvatar, ProfileInfo, ProfilesSection} from "../../components/profiles";
import {PostCreateForm} from "../../components/posts/PostCreateForm";
import {PostsList} from "../../components/posts/PostsList";

interface ProfilePageProps {
    userId: string;
}

export const ProfilePage = observer<RouteComponentProps<ProfilePageProps>>(({match}) => {
    const rootStore = useContext(RootStoreContext);
    const {
        loadingPage,
        user,
        loadUser,
        profileDetails,
        loadingProfileDetails
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
    }, [match.params.userId, loadUser, loadProfileFriends, loadProfileDetails])

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
            </Grid.Column>
            <Grid.Column width={11}>
                <ProfileInfo
                    firstName={user?.firstName}
                    lastName={user?.lastName}
                    profileDetails={profileDetails!}
                    loading={loadingProfileDetails}
                />
                {user?.isLoggedInUser && <PostCreateForm/>}
                <PostsList userId={match.params.userId}/>
            </Grid.Column>
        </Grid>
    );
});