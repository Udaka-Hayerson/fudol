import { useQuery } from "@tanstack/react-query";

import { USER_LIST } from "../constants/queries";

export default function useUserList() {
    return useQuery({
        queryKey: [USER_LIST],
        queryFn: async function getUserList() {
            const b = await fetch("http://localhost:8080/userlist")
            return await b.json()
        },
    })
}
