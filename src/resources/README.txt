JXCSS 1.0

JXCSS is a SAX-generating CSS2 parser adapter written in Java. JXCSS can be used
to easily instrument or refactor existing CSS stylesheets as well as to
dynamically generate new ones.

JXCSS is published under the Apache License 2.0

JXCSS can be fed from any SAC-compliant CSS2 parser. As the parser processes a
stylesheet, JXCSS produces a stream of SAX events that model the stylesheet'
deep structure. JXCSS can be connected to any SAX content handler, such as an
XSLT or STX transformation.

The generated XML conforms with JXCSS's own DTD (XCSS), which models stylesheets
in accordance to the W3C Level-2 DOM. An alternative, compact syntax is also
available.

JXCSS has been tested with the Batik 1.6 Steady State 0.9.4, Flute 1.3 and
SAC CSS2 parsers. 

Comments and inquiries can be sent to Ricardo Rocha (ricardo@apache.org)

