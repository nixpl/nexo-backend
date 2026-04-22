package pl.edu.uj.tp.nexo.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.uj.tp.nexo.board.dto.BoardResponse;
import pl.edu.uj.tp.nexo.board.dto.CreateBoardRequest;
import pl.edu.uj.tp.nexo.board.dto.UpdateStageRequest;
import pl.edu.uj.tp.nexo.board.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@Tag(name = "Boards", description = "Board management endpoints")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "List boards for an organization")
    public List<BoardResponse> getBoards(@RequestParam Long organizationId) {
        return boardService.getBoardsByOrganization(organizationId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get detailed board info")
    public BoardResponse getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new board")
    public BoardResponse createBoard(@RequestBody CreateBoardRequest request) {
        return boardService.createBoard(request);
    }

    @PutMapping("/{id}/stages/{stageId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update stage status (active/inactive)")
    public BoardResponse updateStage(
            @PathVariable Long id,
            @PathVariable Long stageId,
            @RequestBody UpdateStageRequest request
    ) {
        return boardService.updateStage(id, stageId, request);
    }

    @PostMapping("/{id}/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a user to the board")
    public BoardResponse addUserToBoard(@PathVariable Long id, @PathVariable Long userId) {
        return boardService.addUserToBoard(id, userId);
    }
}
