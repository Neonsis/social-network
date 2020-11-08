import React, {useContext, useEffect} from "react";
import {AppNavBar} from "./components/navigation";
import {Route, Switch} from "react-router-dom";
import {AuthLayout} from "./pages/auth";
import {RootStoreContext} from "./stores/rootStore";
import {observer} from "mobx-react-lite";
import {LoadingComponent} from "./components/layot";

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
        <React.Fragment>
            <AppNavBar/>
            <Switch>
                {user
                    ? <div></div>
                    : <Route exact render={() => <AuthLayout/>}/>
                }
            </Switch>
        </React.Fragment>
    );
}

export default observer(App);
