import { StrictMode } from "react"
import { createRoot } from "react-dom/client"
import { QueryClient, QueryClientProvider, MutationCache } from "@tanstack/react-query"
// import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { toast, ToastContainer } from "react-toastify"
import 'react-toastify/dist/ReactToastify.css';

import "./index.css"
import Router from "./router"

const queryClient = new QueryClient({
	mutationCache: new MutationCache({
		onError: (error, _, __, mutation) => {
			toast.error("Something doesn't work..")
		},
		onSuccess: (_, __, ___, mutation) => {
			toast.success("Successfuly")
		},
	}),
})

createRoot(document.getElementById("root")).render(
	<StrictMode>
		<QueryClientProvider client={queryClient}>
			{/* <ReactQueryDevtools initialIsOpen={false} /> */}
			<Router />
            <ToastContainer
				position="bottom-right"
				autoClose={5000}
				hideProgressBar={false}
				newestOnTop={false}
				closeOnClick
				rtl={false}
				pauseOnFocusLoss
				draggable
				pauseOnHover
				theme="light"
			/>
		</QueryClientProvider>
	</StrictMode>
)
