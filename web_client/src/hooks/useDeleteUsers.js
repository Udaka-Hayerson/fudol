import { useMutation, useQueryClient } from "@tanstack/react-query"

import { USER_LIST } from "../constants/queries"

export default function useDeleteUsers() {
	const queryClient = useQueryClient()

	return useMutation({
		mutationFn: async function (ids) {
			const url = new URL(window.location.origin)

			url.searchParams.append("ids", ids)
			const res = await fetch(`/api/adm/users?${url.searchParams}`, { method: "DELETE", body: ids })

			if (!res.ok) {
				throw new Error(res.json())
			}
		},
		onSuccess: (d, v) => {
			const data = queryClient.getQueryData([USER_LIST])

			queryClient.refetchQueries({ queryKey: [USER_LIST] })
			queryClient.setQueryData(
				[USER_LIST],
				data.filter(i => !v.includes(i))
			)
		},
	})
}
