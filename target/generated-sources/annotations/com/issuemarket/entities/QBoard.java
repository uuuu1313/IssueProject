package com.issuemarket.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 517129119L;

    public static final QBoard board = new QBoard("board");

    public final QBaseMemberEntity _super = new QBaseMemberEntity(this);

    public final StringPath bId = createString("bId");

    public final StringPath bName = createString("bName");

    public final StringPath category = createString("category");

    public final EnumPath<com.issuemarket.commons.constants.Role> commentAccessRole = createEnum("commentAccessRole", com.issuemarket.commons.constants.Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final EnumPath<com.issuemarket.commons.constants.Role> listAccessRole = createEnum("listAccessRole", com.issuemarket.commons.constants.Role.class);

    public final StringPath locationAfterWriting = createString("locationAfterWriting");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final ListPath<Post, QPost> postList = this.<Post, QPost>createList("postList", Post.class, QPost.class, PathInits.DIRECT2);

    public final EnumPath<com.issuemarket.commons.constants.Role> replyAccessRole = createEnum("replyAccessRole", com.issuemarket.commons.constants.Role.class);

    public final NumberPath<Integer> rowsOfPage = createNumber("rowsOfPage", Integer.class);

    public final BooleanPath showViewList = createBoolean("showViewList");

    public final BooleanPath use = createBoolean("use");

    public final BooleanPath useAttachFile = createBoolean("useAttachFile");

    public final BooleanPath useAttachImage = createBoolean("useAttachImage");

    public final BooleanPath useComment = createBoolean("useComment");

    public final BooleanPath useEditor = createBoolean("useEditor");

    public final BooleanPath useReply = createBoolean("useReply");

    public final EnumPath<com.issuemarket.commons.constants.Role> viewAccessRole = createEnum("viewAccessRole", com.issuemarket.commons.constants.Role.class);

    public final EnumPath<com.issuemarket.commons.constants.Role> writeAccessRole = createEnum("writeAccessRole", com.issuemarket.commons.constants.Role.class);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

