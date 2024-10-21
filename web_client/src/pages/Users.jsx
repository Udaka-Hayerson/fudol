import React, { useState } from "react"
import { styled } from "@mui/material/styles"
import Table from "@mui/material/Table"
import TableCell, { tableCellClasses } from "@mui/material/TableCell"
import TableBody from "@mui/material/TableBody"
import TableContainer from "@mui/material/TableContainer"
import TableHead from "@mui/material/TableHead"
import TableRow from "@mui/material/TableRow"
import Paper from "@mui/material/Paper"
import { Checkbox, LinearProgress, Typography } from "@mui/material"
import DeleteIcon from "@mui/icons-material/Delete"
import Tooltip from "@mui/material/Tooltip"
import IconButton from "@mui/material/IconButton"

import useUserList from "../hooks/useUserList"
import useDeleteUsers from "../hooks/useDeleteUsers"

const StyledTableCell = styled(TableCell)(({ theme }) => ({
	[`&.${tableCellClasses.head}`]: {
		backgroundColor: theme.palette.common.black,
		color: theme.palette.common.white,
	},
	[`&.${tableCellClasses.body}`]: {
		fontSize: 14,
	},
}))

const StyledTableRow = styled(TableRow)(({ theme }) => ({
	"&:nth-of-type(odd)": {
		backgroundColor: theme.palette.action.hover,
	},
	"&:last-child td, &:last-child th": {
		border: 0,
	},
}))

const Users = () => {
	const { data, isPending } = useUserList()
	const { mutate, isFetching } = useDeleteUsers()
	const [selectedItems, setSelectedItems] = useState([])

	const handleCheckbox = id => {
		setSelectedItems(selectedItems.includes(id) ? selectedItems.filter(k => k !== id) : [...selectedItems, id])
	}

	const removeSelectedItems = () => {
		mutate(selectedItems)
	}

	return (
		<div>
			<Paper>
				{(isFetching || isPending) && <LinearProgress />}
				<TableContainer component={Paper}>
					<Table stickyHeader aria-label="sticky customized table">
						<TableHead>
							<TableRow>
								<StyledTableCell>
									{!!selectedItems.length && (
										<Tooltip title="Delete">
											<IconButton size="small" onClick={removeSelectedItems}>
												<DeleteIcon color="error" />
											</IconButton>
										</Tooltip>
									)}
								</StyledTableCell>
								<StyledTableCell>Id</StyledTableCell>
								<StyledTableCell>Login</StyledTableCell>
								<StyledTableCell>Nickname</StyledTableCell>
								<StyledTableCell>Password</StyledTableCell>
								<StyledTableCell>Birthday</StyledTableCell>
								<StyledTableCell>Expected salary</StyledTableCell>
								<StyledTableCell>Time count</StyledTableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{data?.map(row => (
								<StyledTableRow key={row.id} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
									<StyledTableCell>
										<Checkbox
											checked={selectedItems.includes(row.id)}
											onChange={() => handleCheckbox(row.id)}
											inputProps={{ "aria-label": "controlled" }}
											size="small"
											color="primary"
										/>
									</StyledTableCell>
									<StyledTableCell component="th" scope="row">
										{row.id}
									</StyledTableCell>
									<StyledTableCell>{row.login}</StyledTableCell>
									<StyledTableCell>{row.nickname}</StyledTableCell>
									<StyledTableCell>{row.password}</StyledTableCell>
									<StyledTableCell>
										{row.birthday || <Typography color="lightgray">empty</Typography>}
									</StyledTableCell>
									<StyledTableCell>{row.expectedSalary}</StyledTableCell>
									<StyledTableCell>{row.timeCount}</StyledTableCell>
								</StyledTableRow>
							))}
						</TableBody>
					</Table>
				</TableContainer>
				{!data?.length && <Typography align="center">no users yet</Typography>}
			</Paper>
		</div>
	)
}

export default Users
