import React, {useContext, useEffect} from "react";
import {AppNavBar} from "./components/navigation";
import {Route, Switch} from "react-router-dom";
import {RootStoreContext} from "./stores/rootStore";
import {observer} from "mobx-react-lite";
import {AppLayout, AuthLayout, LoadingComponent} from "./components/layouts";
import 'react-notifications-component/dist/theme.css';
import ReactNotification from 'react-notifications-component'

/**
 * Root component of the app
 */
function App() {
    const rootStore = useContext(RootStoreContext);
    const {token, setAppLoaded, appLoaded} = rootStore.commonStore;
    const {getUser, user} = rootStore.userStore;

    useEffect(() => {
        if (token) {
            getUser().finally(() => setAppLoaded())
        } else {
            setAppLoaded();
        }
    }, [getUser, setAppLoaded, token])

    if (!appLoaded) return <LoadingComponent content="Loading app..."/>

    return (
        <div className="app social-network">
            <ReactNotification/>
            <AppNavBar/>
            <Switch>
                {user
                    ? <Route exact render={() => <AppLayout/>}/>
                    : <Route exact render={() => <AuthLayout/>}/>
                }
            </Switch>
        </div>
    );
}

export default observer(App);
