package com.jy.common.utils.weixin.aes;

import java.io.*;
import org.xml.sax.helpers.*;
import java.util.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

class XMLParse
{
    public static Object[] extract(final InputStream is) throws AesException {
        try {
            final SAXParserFactory sax = SAXParserFactory.newInstance();
            final SAXParser parser = sax.newSAXParser();
            final Map<String, Object[]> map = new HashMap<String, Object[]>();
            final DefaultHandler handler = new DefaultHandler() {
                private Object[] result = new Object[3];
                private String temp;
                
                @Override
                public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {
                    super.startElement(uri, localName, qName, attributes);
                }
                
                @Override
                public void endElement(final String uri, final String localName, final String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("Encrypt")) {
                        this.result[1] = this.temp;
                        return;
                    }
                    if (qName.equalsIgnoreCase("ToUserName")) {
                        this.result[2] = this.temp;
                        return;
                    }
                    if (qName.equalsIgnoreCase("xml")) {
                        this.result[0] = 0;
                        map.put("result", this.result);
                    }
                }
                
                @Override
                public void characters(final char[] ch, final int start, final int length) throws SAXException {
                    this.temp = new String(ch, start, length);
                }
            };
            parser.parse(is, handler);
            return map.get("result");
        }
        catch (Exception e) {
            throw new AesException(-40002);
        }
    }
}
