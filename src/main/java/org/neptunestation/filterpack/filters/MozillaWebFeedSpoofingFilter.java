package org.neptunestation.filterpack.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MozillaWebFeedSpoofingFilter extends XMLTransformFilter {
    public static String TRIGGER_HEADER = "TRIGGER_HEADER";
    public static String TRIGGER_HEADER_REGEXP = "TRIGGER_HEADER_REGEXP";

    protected String header = "User-Agent";
    protected String headerRegexp = ".*Mozilla.*";

    @Override protected String getXSL () {
        return 
            "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" +
            "  <xsl:template match=\"processing-instruction()\">" +
            "    <xsl:copy/>" +
            "  </xsl:template>" +
            "  <xsl:template match=\"/\">" +
            "    <xsl:apply-templates select=\"processing-instruction()\"/>" +
            "    <xsl:comment>" +
            "      ********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************" +
            "    </xsl:comment>" +
            "    <xsl:copy-of select=\"*\"/>" +
            "  </xsl:template>" +
            "</xsl:stylesheet>";}

    @Override public void init (FilterConfig filterConfig) {
        if (filterConfig.getInitParameter(TRIGGER_HEADER)!=null) header = filterConfig.getInitParameter(TRIGGER_HEADER);
        if (filterConfig.getInitParameter(TRIGGER_HEADER_REGEXP)!=null) headerRegexp = filterConfig.getInitParameter(TRIGGER_HEADER_REGEXP);
        super.init(filterConfig);}

    @Override protected void doFilter (HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if ((req.getHeader(header)+"").matches(headerRegexp)) chain.doFilter(req, wrapResponse(res));
        else chain.doFilter(req, res);}}
