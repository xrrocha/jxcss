<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
                xmlns:xcss="xcss/1.0"
                xmlns="xcss-compact/1.0"
				exclude-result-prefixes="xcss"
				xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml"
                indent="yes"
                doctype-public="xcss"
                doctype-system="xcss/1.0"/>

    <xsl:template match="xcss:css-stylesheet">
        <compact-css-stylesheet>
            <xsl:apply-templates/>
        </compact-css-stylesheet>
    </xsl:template>

    <xsl:template match="xcss:style">
        <style>
            <xsl:for-each select="xcss:element-node-selector|xcss:conditional-selector">
                <selector>
                    <xsl:apply-templates select="."/>
                </selector>
            </xsl:for-each>
            <xsl:apply-templates select="xcss:property"/>
        </style>
    </xsl:template>

    <xsl:template match="xcss:element-node-selector">
        <xsl:value-of select="@local-name"/>
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="xcss:conditional-selector">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="xcss:and-condition">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="xcss:class-condition">
        <xsl:text>.</xsl:text>
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="xcss:pseudo-class-condition">
        <xsl:text>:</xsl:text>
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="xcss:property">
        <property name="{@name}">
            <xsl:attribute name="value">
                <xsl:apply-templates/>
            </xsl:attribute>
        </property>
    </xsl:template>

    <xsl:template match="xcss:function">
        <xsl:value-of select="@name"/>
        <xsl:text>(</xsl:text>
        <xsl:for-each select="*">
            <xsl:if test="position() &gt; 1">
                <xsl:text>,</xsl:text>
            </xsl:if>
            <xsl:apply-templates select="."/>
        </xsl:for-each>
        <xsl:text>)</xsl:text>
    </xsl:template>

    <xsl:template match="xcss:value">
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="xcss:unit">
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="xcss:unit">
        <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="xcss:comment">
        <comment>
            <xsl:value-of select="."/>
        </comment>
    </xsl:template>
</xsl:stylesheet>
    