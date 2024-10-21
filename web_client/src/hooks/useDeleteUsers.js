import { useMutation, useQueryClient } from "@tanstack/react-query"

import { USER_LIST } from "../constants/queries"

export default function useDeleteUsers() {
	const queryClient = useQueryClient()

	return useMutation({
		mutationFn: async function (ids) {
			const url = new URL(window.location.origin)

			url.searchParams.append("ids", ids)
			await fetch(`/api/adm/users?${url.searchParams}`, { method: "DELETE", body: ids })
		},
		onSuccess: () => {
			queryClient.refetchQueries({ queryKey: [USER_LIST] })
		},
	})
}
