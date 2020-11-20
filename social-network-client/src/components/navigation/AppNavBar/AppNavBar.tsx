import React, {useContext} from "react";
import "./AppNavBar.scss";
import {observer} from "mobx-react-lite"
import {Container, Dropdown, Image, Menu} from "semantic-ui-react";
import {NavLink} from "react-router-dom";
import AvatarNotFound from "../../../assets/avatar_not_found.png";
import {ReactComponent as Logo} from "../../../assets/logo.svg";
import {RootStoreContext} from "../../../stores/rootStore";

export const AppNavBar = observer(() => {
    const rootStore = useContext(RootStoreContext);
    const {user, logout} = rootStore.userStore;

    return (
        <div className="app-navbar-wrapper">
            <Menu fixed="top" className="app-navbar nav" text>
                <Container>
                    <Menu.Item header as={NavLink} exact to="/">
                        <Logo className="app-navbar__logo"/>
                    </Menu.Item>
                    {user && (
                        <Menu.Item position="right" className="app-navbar__profile">
                            <Dropdown
                                trigger={
                                    <React.Fragment>
                                        <span className="app-navbar__profile-name">{user.firstName}</span>
                                        <Image avatar src={user.avatarUrl ? user.avatarUrl : AvatarNotFound}/>
                                    </React.Fragment>
                                }
                                className="app-navbar__dropdown"
                                pointing="top left"
                            >
                                <Dropdown.Menu>
                                    <Dropdown.Item onClick={logout} text="Выйти"/>
                                </Dropdown.Menu>
                            </Dropdown>
                        </Menu.Item>
                    )}
                </Container>
            </Menu>
        </div>
    );
});

