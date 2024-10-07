import React from "react"
import { useLocation, useNavigate, useRoutes } from "react-router-dom";
import { Outlet } from 'react-router-dom';

import FavoriteIcon from '@mui/icons-material/Favorite';
import PeopleIcon from '@mui/icons-material/People';
import { List, ListItemButton, ListItemIcon, ListItemText } from "@mui/material";

const MainLayout = () => {
    const navigate = useNavigate();
    const { pathname } = useLocation();

    const handleChange = (newValue) => {
        navigate(newValue);
    };

    return (
        <div className="flex p-2">
            <div className="flex flex-col">
                <List
                    sx={{ width: '100%', maxWidth: 360, bgcolor: 'background.paper' }}
                    component="nav"
                    aria-labelledby="nested-list-subheader"
                >
                    <ListItemButton selected={pathname === '/users'} onClick={() => handleChange("users")}>
                        <ListItemIcon>
                            <PeopleIcon />
                        </ListItemIcon>
                        <ListItemText primary="users" />
                    </ListItemButton>
                    {/* <ListItemButton selected={pathname === "/fav"} onClick={() => handleChange("fav")}>
                        <ListItemIcon>
                            <FavoriteIcon />
                        </ListItemIcon>
                        <ListItemText primary="Drafts" />
                    </ListItemButton> */}
                </List>
            </div>
            <main className="p-2 w-full">
                <Outlet />
            </main>
        </div>
    )
}

export default MainLayout;

