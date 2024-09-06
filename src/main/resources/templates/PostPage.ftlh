<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#-- @ftlvariable name="comments" type="ru.itmo.wp.domain.Comment[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post/>
        <h3>Comments</h3>
        <#if user??>
            <div class="form">
                <div class="header">Add a New Comment</div>
                <div class="body">
                    <form method="post" action="">
                        <input type="hidden" name="action" value="addComment"/>
                        <div class="field">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text"></textarea>
                            </div>
                        </div>
<#--                        <@c.error "comment.text"/>-->
                        <div class="button-field">
                            <input type="submit" value="Add">
                        </div>
                    </form>
                </div>
            </div>
        </#if>
        <#list post.comments?sort_by("id") as comment>
            <@c.comment comment/>
        </#list>
    <#else>
        No such post.
    </#if>
</@c.page>