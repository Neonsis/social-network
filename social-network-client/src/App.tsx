import React from "react";
import {AppNavBar} from "./components/navigation";
import {Route, Switch} from "react-router-dom";
import {AuthLayout} from "./pages/auth";

function App() {

    const user = null;

    return (
        <React.Fragment>
            <AppNavBar/>
            <Switch>
                {user ? (
                    <div></div>
                ) : (
                    <Route exact render={() => <AuthLayout/>}/>
                )}
            </Switch>
        </React.Fragment>
    );
}

export default App;
