import { useQuery } from "@tanstack/react-query"

import { USER_LIST } from "../constants/queries"

export default function useUserList() {
	return useQuery({
		queryKey: [USER_LIST],
		queryFn: async function () {
			const b = await fetch(`/api/adm/users`)
			return await b.json()
		},
	})
}
