package com.issuemarket.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPostView is a Querydsl query type for PostView
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostView extends EntityPathBase<PostView> {

    private static final long serialVersionUID = -1107706868L;

    public static final QPostView postView = new QPostView("postView");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath uid = createString("uid");

    public QPostView(String variable) {
        super(PostView.class, forVariable(variable));
    }

    public QPostView(Path<? extends PostView> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostView(PathMetadata metadata) {
        super(PostView.class, metadata);
    }

}

