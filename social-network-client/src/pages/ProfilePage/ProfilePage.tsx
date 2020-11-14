import React, {useContext, useEffect} from "react";
import {Grid} from "semantic-ui-react";
import {RouteComponentProps} from "react-router";
import {RootStoreContext} from "../../stores/rootStore";
import {observer} from "mobx-react-lite";
import {ProfileAvatar, ProfileInfo, ProfilesSection} from "../../components/profiles";
import {PostCreateForm} from "../../components/posts/PostCreateForm";
import {Post} from "../../components/posts/Post";

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
        addToFriends,
        deleteFriendship,
        cancelFriendship,
        confirmFriendship,
        loadingFriends
    } = rootStore.friendshipStore;
    const {
        loadUserPosts,
        userPosts
    } = rootStore.postStore;
    const {loadProfileDetails} = rootStore.profileStore;

    useEffect(() => {
        const userId = match.params.userId;
        loadUser(userId);
        loadProfileFriends(userId)
        loadProfileDetails(userId);
        loadUserPosts(userId, 0);
    }, [match.params.userId, loadUser, loadProfileFriends, loadUserPosts])


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
                    addToFriends={addToFriends}
                    deleteFriendship={deleteFriendship}
                    cancelFriendship={cancelFriendship}
                    confirmFriendship={confirmFriendship}
                />
                <ProfilesSection
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
                {userPosts && userPosts.map(post => (
                    <Post post={post}/>
                ))}
            </Grid.Column>
        </Grid>
    );
});