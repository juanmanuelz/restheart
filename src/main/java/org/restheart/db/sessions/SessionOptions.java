/*
 * RESTHeart - the Web API for MongoDB
 * Copyright (C) SoftInstigate Srl
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.restheart.db.sessions;

import com.google.common.base.Objects;
import java.util.UUID;
import static org.restheart.db.sessions.Sid.longToBytes;

/**
 *
 * @author Andrea Di Cesare <andrea@softinstigate.com>
 */
public class SessionOptions {
    public static final int TXN_FLAG = 0x20; // 0010 0000
    public static final int CAUSALLY_CONSISTENT_FLAG = 0x10; // 0001 0000

    private final boolean transacted;
    private final boolean causallyConsistent;

    public SessionOptions(boolean transacted, boolean causallyConsistent) {
        this.transacted = transacted;
        this.causallyConsistent = causallyConsistent;
    }

    public SessionOptions(UUID sid) {
        var lsb = longToBytes(sid.getLeastSignificantBits());

        this.transacted = (lsb[0] & TXN_FLAG) 
                == TXN_FLAG;
        
        this.causallyConsistent = (lsb[0] & CAUSALLY_CONSISTENT_FLAG) 
                == CAUSALLY_CONSISTENT_FLAG;
    }

    /**
     * @return the transacted
     */
    public boolean isTransacted() {
        return transacted;
    }

    /**
     * @return the causallyConsistent
     */
    public boolean isCausallyConsistent() {
        return causallyConsistent;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        var other = (SessionOptions) obj;

        return Objects.equal(this.transacted, other.transacted)
                && Objects.equal(this.causallyConsistent, other.causallyConsistent);
    }

    @Override
    public String toString() {
        return "SessionOptions(" 
                + this.transacted
                + ", "
                + this.causallyConsistent
                + ")";
    }
}
