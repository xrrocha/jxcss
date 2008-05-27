<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xcss="xcss/1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text"/>
	
	<xsl:template match="xcss:css-stylesheet">
		<xsl:apply-templates select="xcss:style"/>
	</xsl:template>
	
	<xsl:template match="xcss:style">
		<xsl:for-each select="xcss:element-node-selector|xcss:conditional-selector|xcss:child-selector|xcss:descendant-selector|xcss:direct-adjacent-selector">
			<xsl:if test="position() > 1">
				<xsl:text>, </xsl:text>
			</xsl:if>
			<xsl:apply-templates select="."/>
		</xsl:for-each>
		
		<xsl:text> {&#10;</xsl:text>
        <xsl:apply-templates select="xcss:property"/>
        <xsl:text>}&#10;&#10;</xsl:text>
    </xsl:template>
    
	<xsl:template match="xcss:child">
		<xsl:apply-templates/>
    </xsl:template>
    
	<xsl:template match="xcss:sibling-selector">
			<xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="xcss:element-node-selector">
    	<xsl:choose>
    		<xsl:when test="string-length(@local-name) > 0">
				<xsl:value-of select="@local-name"/>
			</xsl:when>
    		<xsl:otherwise>
				<xsl:text>*</xsl:text>
			</xsl:otherwise>
    	</xsl:choose>
    </xsl:template>
    
    <xsl:template match="xcss:conditional-selector|xcss:first-condition|xcss:second-condition">
		<xsl:if test="string-length(xcss:selector/xcss:element-node-selector/@local-name) != 0">
			<xsl:value-of select="xcss:selector/xcss:element-node-selector/@local-name"/>
		</xsl:if>
		<xsl:if test="xcss:class-condition">
			<xsl:text>.</xsl:text>
			<xsl:value-of select="xcss:class-condition"/>
		</xsl:if>
		<xsl:if test="xcss:id-condition">
			<xsl:text>#</xsl:text>
			<xsl:value-of select="xcss:id-condition"/>
		</xsl:if>
		<xsl:if test="xcss:pseudo-class-condition">
			<xsl:text>:</xsl:text>
			<xsl:value-of select="xcss:pseudo-class-condition"/>
		</xsl:if>
		<xsl:apply-templates select="xcss:and-condition"/>
		<xsl:apply-templates select="xcss:attribute-condition"/>
		<xsl:apply-templates select="xcss:one-of-attribute-condition"/>
		<xsl:apply-templates select="xcss:begin-hyphen-attribute-condition"/>
		<xsl:apply-templates select="xcss:lang-condition"/>
    </xsl:template>

    <xsl:template match="xcss:pseudo-element-selector">
        <xsl:text>:</xsl:text>
		<xsl:value-of select="@local-name"/>
	</xsl:template>    

    <xsl:template match="xcss:child-selector">
		<xsl:choose>
			<xsl:when test="xcss:child/xcss:pseudo-element-selector">
				<xsl:apply-templates select="xcss:ancestor"/>
				<xsl:text>:</xsl:text>
				<xsl:value-of select="xcss:child/xcss:pseudo-element-selector/@local-name"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates select="xcss:ancestor"/>
		        <xsl:text> &gt; </xsl:text>		
				<xsl:apply-templates select="xcss:child"/>
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>    

    <xsl:template match="xcss:direct-adjacent-selector">
		<xsl:apply-templates select="xcss:selector"/>
        <xsl:text> + </xsl:text>
		<xsl:apply-templates select="xcss:sibling-selector"/>
	</xsl:template>    

    <xsl:template match="xcss:descendant-selector">
		<xsl:apply-templates select="xcss:ancestor"/>
        <xsl:text> </xsl:text>
		<xsl:apply-templates select="xcss:child"/>
	</xsl:template>
	
    <xsl:template match="xcss:and-condition">
		<xsl:apply-templates select="xcss:first-condition"/>
		<xsl:apply-templates select="xcss:second-condition"/>
    </xsl:template>

    <xsl:template match="xcss:attribute-condition">
        <xsl:text>[</xsl:text>
		<xsl:value-of select="@local-name"/>
		<xsl:if test="text()">
        	<xsl:text> = </xsl:text>
			<xsl:value-of select="text()"/>
		</xsl:if>
        <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="xcss:one-of-attribute-condition">
        <xsl:text>[</xsl:text>
		<xsl:value-of select="@local-name"/>
       	<xsl:text> ~= </xsl:text>
		<xsl:value-of select="text()"/>
        <xsl:text>]</xsl:text>
    </xsl:template>
    
    <xsl:template match="xcss:begin-hyphen-attribute-condition">
        <xsl:text>[</xsl:text>
		<xsl:value-of select="@local-name"/>
       	<xsl:text> |= </xsl:text>
		<xsl:value-of select="text()"/>
        <xsl:text>]</xsl:text>
    </xsl:template>

    <xsl:template match="xcss:lang-condition">
        <xsl:text>:lang(</xsl:text>
		<xsl:value-of select="@lang"/>
        <xsl:text>)</xsl:text>
    </xsl:template>

    <xsl:template match="xcss:property">
        <xsl:text>&#9;</xsl:text>
        <xsl:value-of select="@name"/>
        <xsl:text>:</xsl:text>
        <xsl:apply-templates select="xcss:value"/>
        <xsl:if test="@important = 'true'">
        <xsl:text> !IMPORTANT</xsl:text>
        </xsl:if>
        <xsl:text>;&#10;</xsl:text>
    </xsl:template>

    <xsl:template match="xcss:value">
        <xsl:text> </xsl:text>
        <xsl:choose>
        	<xsl:when test="@type = 'string' and contains(text(), ' ')">
        		<xsl:text>"</xsl:text>
		        <xsl:value-of select="text()"/>
		        <xsl:text>"</xsl:text>
        	</xsl:when>
        	<xsl:otherwise>
		        <xsl:value-of select="text()"/>
		        <xsl:if test="@unit">
		            <xsl:value-of select="@unit"/>
		        </xsl:if>
        	</xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="text()"/>

</xsl:stylesheet>
