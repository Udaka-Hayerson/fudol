import React from "react"
import { styled } from "@mui/material/styles"
import Table from "@mui/material/Table"
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableBody from "@mui/material/TableBody"
import TableContainer from "@mui/material/TableContainer"
import TableHead from "@mui/material/TableHead"
import TableRow from "@mui/material/TableRow"
import Paper from "@mui/material/Paper"
import { LinearProgress, Typography } from "@mui/material"

import useUserList from "../hooks/useUserList"

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
	// hide last border
	"&:last-child td, &:last-child th": {
		border: 0,
	},
}))

const Users = () => {
	const { data, isFetching } = useUserList()

	return (
		<div>
			<Paper>
				{isFetching && <LinearProgress />}
				<TableContainer component={Paper}>
					<Table stickyHeader size="small" aria-label="sticky customized table">
						<TableHead>
							<TableRow>
								<StyledTableCell>Id</StyledTableCell>
								<StyledTableCell align="right">Login</StyledTableCell>
								<StyledTableCell align="right">Nickname</StyledTableCell>
								<StyledTableCell align="right">Password</StyledTableCell>
								<StyledTableCell align="right">Birthday</StyledTableCell>
								<StyledTableCell align="right">Expected salary</StyledTableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{data?.map(row => (
								<StyledTableRow key={row.id} sx={{ "&:last-child td, &:last-child th": { border: 0 } }}>
									<StyledTableCell component="th" scope="row">
										{row.id}
									</StyledTableCell>
									<StyledTableCell align="right">{row.login}</StyledTableCell>
									<StyledTableCell align="right">{row.nickname}</StyledTableCell>
									<StyledTableCell align="right">{row.password}</StyledTableCell>
									<StyledTableCell align="right">
                                        {row.birthday || <Typography color='lightgray'>empty</Typography>}
                                    </StyledTableCell>
									<StyledTableCell align="right">{row.expectedSalary}</StyledTableCell>
								</StyledTableRow>
							))}
						</TableBody>
					</Table>
				</TableContainer>
			</Paper>
		</div>
	)
}

export default Users
