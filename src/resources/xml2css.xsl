<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
				xmlns:xcss="xcss/1.0"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="text"/>

    <xsl:template match="xcss:css-stylesheet">
        <xsl:apply-templates select="xcss:style"/>
    </xsl:template>

    <xsl:template match="xcss:style">
        <xsl:variable name="selector-name">
            <xsl:choose>
                <xsl:when test="xcss:element-node-selector">
                    <xsl:value-of select="xcss:element-node-selector/@local-name"/>
                </xsl:when>
                <xsl:when test="xcss:conditional-selector">
                    <xsl:if test="string-length(xcss:conditional-selector/xcss:selector/xcss:element-node-selector/@local-name) != 0">
                        <xsl:value-of select="xcss:conditional-selector/xcss:selector/xcss:element-node-selector/@local-name"/>
                    </xsl:if>
                    <xsl:text>.</xsl:text>
                    <xsl:value-of select="xcss:conditional-selector/xcss:class-condition"/>
                </xsl:when>
            </xsl:choose>
        </xsl:variable>

        <xsl:value-of select="$selector-name"/>
        <xsl:text> {&#10;</xsl:text>
        <xsl:apply-templates select="xcss:property"/>
        <xsl:text>}&#10;&#10;</xsl:text>
    </xsl:template>

    <xsl:template match="xcss:property">
        <xsl:text>&#9;</xsl:text>
        <xsl:value-of select="@name"/>
        <xsl:text>: </xsl:text>
        <xsl:apply-templates select="xcss:value"/>
        <xsl:text>;&#10;</xsl:text>
    </xsl:template>

    <xsl:template match="xcss:value">
        <xsl:value-of select="string(.)"/>
        <xsl:if test="@unit">
            <xsl:value-of select="@unit"/>
        </xsl:if>
    </xsl:template>

    <xsl:template match="text()"/>
</xsl:stylesheet>
